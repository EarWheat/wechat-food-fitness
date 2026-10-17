package com.tencent.wxcloudrun.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 智谱大模型客户端：GLM-4V 视觉识别 + GLM-4 文本生成。
 */
@Service
public class AiService {

  private static final String ZHIPU_URL = "https://open.bigmodel.cn/api/paas/v4/chat/completions";

  private static final String NUTRITION_SYSTEM_PROMPT =
      "你是营养分析助手。根据食物图片估算其营养数据。只返回 JSON，不要任何其他文字、解释或 markdown。格式严格如下："
      + "{\"foodName\":\"食物名\",\"items\":[{\"name\":\"...\",\"calories\":0,\"protein\":0,\"carbs\":0,\"fat\":0}],"
      + "\"total\":{\"calories\":0,\"protein\":0,\"carbs\":0,\"fat\":0},\"serving\":\"1份\",\"confidence\":0.8,\"note\":\"...\"}"
      + "单位：calories=kcal，protein/carbs/fat=克。若不确定食材或份量，confidence 取低值并在 note 中说明不确定性。";

  private static final String ADVICE_SYSTEM_PROMPT =
      "你是私人营养顾问。根据用户的身体档案、每日目标和近期饮食记录，给出简洁、具体、可执行的中文饮食建议（100-200 字）。只输出建议正文。";

  @Value("${zhipu.api-key:}")
  private String apiKey;

  @Value("${zhipu.vision-model:glm-4v-plus}")
  private String visionModel;

  @Value("${zhipu.text-model:glm-4-flash}")
  private String textModel;

  private final RestTemplate restTemplate = new RestTemplate();
  private final ObjectMapper objectMapper = new ObjectMapper();

  public Map<String, Object> analyzeFood(String imageBase64) {
    if (imageBase64 == null || imageBase64.isEmpty()) {
      throw new IllegalArgumentException("imageBase64 is empty");
    }

    List<Object> userContent = new ArrayList<>();
    Map<String, Object> textPart = new HashMap<>();
    textPart.put("type", "text");
    textPart.put("text", "请分析这张食物图片的营养数据");
    userContent.add(textPart);

    Map<String, Object> imagePart = new HashMap<>();
    imagePart.put("type", "image_url");
    Map<String, Object> imageUrl = new HashMap<>();
    imageUrl.put("url", "data:image/jpeg;base64," + imageBase64);
    imagePart.put("image_url", imageUrl);
    userContent.add(imagePart);

    Map<String, Object> userMsg = new HashMap<>();
    userMsg.put("role", "user");
    userMsg.put("content", userContent);

    List<Object> messages = new ArrayList<>();
    messages.add(systemMsg(NUTRITION_SYSTEM_PROMPT));
    messages.add(userMsg);

    String raw = chat(visionModel, messages);
    return parseNutritionJson(raw);
  }

  public String generateAdvice(String summary, Map<String, Object> profile, Map<String, Object> targets) {
    Map<String, Object> data = new HashMap<>();
    data.put("身体档案", profile);
    data.put("每日目标", targets);
    data.put("近期饮食", summary);

    List<Object> messages = new ArrayList<>();
    messages.add(systemMsg(ADVICE_SYSTEM_PROMPT));
    Map<String, Object> userMsg = new HashMap<>();
    userMsg.put("role", "user");
    userMsg.put("content", toJsonString(data));
    messages.add(userMsg);

    return chat(textModel, messages);
  }

  private Map<String, Object> systemMsg(String content) {
    Map<String, Object> msg = new HashMap<>();
    msg.put("role", "system");
    msg.put("content", content);
    return msg;
  }

  private String chat(String model, List<Object> messages) {
    Map<String, Object> body = new HashMap<>();
    body.put("model", model);
    body.put("messages", messages);

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.setBearerAuth(apiKey);

    HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);
    String resp = restTemplate.postForObject(ZHIPU_URL, entity, String.class);
    return extractContent(resp);
  }

  private String extractContent(String respBody) {
    try {
      JsonNode root = objectMapper.readTree(respBody);
      return root.path("choices").get(0).path("message").path("content").asText();
    } catch (Exception e) {
      throw new RuntimeException("解析大模型响应失败: " + e.getMessage(), e);
    }
  }

  private String toJsonString(Object obj) {
    try {
      return objectMapper.writeValueAsString(obj);
    } catch (Exception e) {
      throw new RuntimeException("序列化失败: " + e.getMessage(), e);
    }
  }

  private Map<String, Object> parseNutritionJson(String rawText) {
    String[] candidates = {
        rawText != null ? rawText.trim() : "",
        stripFences(rawText),
        extractFirstJson(rawText)
    };
    for (String candidate : candidates) {
      if (candidate == null || candidate.isEmpty()) {
        continue;
      }
      try {
        JsonNode node = objectMapper.readTree(candidate);
        if (node.has("total") && node.get("total").isObject()) {
          return objectMapper.convertValue(node, Map.class);
        }
      } catch (Exception ignored) {
        // 尝试下一个候选
      }
    }
    throw new RuntimeException("无法解析营养 JSON");
  }

  private String stripFences(String text) {
    if (text == null) {
      return "";
    }
    return text.replaceAll("```(?:json)?", "").replaceAll("```", "").trim();
  }

  private String extractFirstJson(String text) {
    if (text == null) {
      return "";
    }
    int start = text.indexOf("{");
    int end = text.lastIndexOf("}");
    if (start < 0 || end < 0 || end <= start) {
      return "";
    }
    return text.substring(start, end + 1);
  }
}
