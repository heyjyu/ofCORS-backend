package kr.heyjyu.ofcors.utils;

import kr.heyjyu.ofcors.exceptions.KakaoPayReadyException;
import kr.heyjyu.ofcors.models.KakaoPayApproval;
import kr.heyjyu.ofcors.models.KakaoPayReady;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

public class KakaoPay {
    private static final String HOST = "https://kapi.kakao.com";

    private KakaoPayReady kakaoPayReady;

    private String orderId;

    private Long userId;

    private String adminKey;

    public KakaoPay(String adminKey) {
        this.adminKey = adminKey;
    }

    public String kakaoPayReady(String orderId,
                                Long userId,
                                String productName,
                                Long quantity,
                                Long totalAmount) {
        this.orderId = orderId;
        this.userId = userId;
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "KakaoAK " + adminKey);
        headers.add("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();

        params.add("cid", "TC0ONETIME");
        params.add("partner_order_id", orderId);
        params.add("partner_user_id", String.valueOf(userId));
        params.add("item_name", productName);
        params.add("quantity", String.valueOf(quantity));
        params.add("total_amount", String.valueOf(totalAmount));
        params.add("tax_free_amount", "0");
        // TODO: 배포 후 url 수정
        params.add("approval_url", "http://localhost:8080/charge/success");
        params.add("cancel_url", "http://localhost:8080/charge/cancel");
        params.add("fail_url", "http://localhost:8080/charge/fail");

        HttpEntity<MultiValueMap<String, String>> body = new HttpEntity<>(params, headers);

        try {
            kakaoPayReady = restTemplate.postForObject(new URI(HOST + "/v1/payment/ready"), body, KakaoPayReady.class);

            return kakaoPayReady.getNext_redirect_pc_url();

        } catch (RestClientException e) {
            throw new KakaoPayReadyException(e);

        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public KakaoPayApproval kakaoPayInfo(String pg_token) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "KakaoAK " + adminKey);
        headers.add("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();

        params.add("cid", "TC0ONETIME");
        params.add("tid", kakaoPayReady.getTid());
        params.add("partner_order_id", orderId);
        params.add("partner_user_id", String.valueOf(userId));
        params.add("pg_token", pg_token);

        HttpEntity<MultiValueMap<String, String>> requestBody = new HttpEntity<>(params, headers);

        try {
            return restTemplate
                    .postForObject(new URI(HOST + "/v1/payment/approve"),
                            requestBody, KakaoPayApproval.class);

        } catch (RestClientException e) {
            throw new KakaoPayReadyException(e);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
