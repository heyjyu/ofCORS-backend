package kr.heyjyu.ofcors.utils;

import kr.heyjyu.ofcors.exceptions.IamPortAccessTokenException;
import kr.heyjyu.ofcors.exceptions.IamPortBankHolderException;
import kr.heyjyu.ofcors.exceptions.KakaoPayReadyException;
import kr.heyjyu.ofcors.models.IamPortAccessToken;
import kr.heyjyu.ofcors.models.IamPortAccountHolder;
import kr.heyjyu.ofcors.models.KakaoPayApproval;
import kr.heyjyu.ofcors.models.KakaoPayReady;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

public class IamPort {
    private static final String HOST = "https://api.iamport.kr";

    private String apiKey;

    private String apiSecret;

    private Map<String, String> bankCodes = new HashMap<>();

    public IamPort(String apiKey, String apiSecret) {
        this.apiKey = apiKey;
        this.apiSecret = apiSecret;

        this.setBankCodes();
    }

    private void setBankCodes() {
        this.bankCodes.put("KB국민은행", "004");
        this.bankCodes.put("SC제일은행", "023");
        this.bankCodes.put("경남은행", "039");
        this.bankCodes.put("광주은행", "034");
        this.bankCodes.put("기업은행", "003");
        this.bankCodes.put("농협", "011");
        this.bankCodes.put("대구은행", "031");
        this.bankCodes.put("부산은행", "032");
        this.bankCodes.put("산업은행", "002");
        this.bankCodes.put("수협", "007");
        this.bankCodes.put("신한은행", "088");
        this.bankCodes.put("신협", "048");
        this.bankCodes.put("외환은행", "005");
        this.bankCodes.put("우리은행", "020");
        this.bankCodes.put("우체국", "071");
        this.bankCodes.put("전북은행", "037");
        this.bankCodes.put("제주은행", "035");
        this.bankCodes.put("축협", "012");
        this.bankCodes.put("하나은행(서울은행)", "081");
        this.bankCodes.put("한국씨티은행(한미은행)", "027");
        this.bankCodes.put("K뱅크", "089");
        this.bankCodes.put("카카오뱅크", "090");
        this.bankCodes.put("유안타증권", "209");
        this.bankCodes.put("현대증권", "218");
        this.bankCodes.put("미래에셋증권", "230");
        this.bankCodes.put("대우증권", "238");
        this.bankCodes.put("삼성증권", "240");
        this.bankCodes.put("한국투자증권", "243");
        this.bankCodes.put("우리투자증권", "247");
        this.bankCodes.put("교보증권", "261");
        this.bankCodes.put("하이투자증권", "262");
        this.bankCodes.put("에이치엠씨투자증권", "263");
        this.bankCodes.put("키움증권", "264");
        this.bankCodes.put("이트레이드증권", "265");
        this.bankCodes.put("에스케이증권", "266");
        this.bankCodes.put("대신증권", "267");
        this.bankCodes.put("솔로몬투자증권", "268");
        this.bankCodes.put("한화증권", "269");
        this.bankCodes.put("하나대투증권", "270");
        this.bankCodes.put("굿모닝신한증권", "278");
        this.bankCodes.put("동부증권", "279");
        this.bankCodes.put("유진투자증권", "280");
        this.bankCodes.put("메리츠증권", "287");
        this.bankCodes.put("엔에이치투자증권", "289");
        this.bankCodes.put("부국증권", "290");
    }

    public String issueAccessToken() {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();

        params.add("imp_key", apiKey);
        params.add("imp_secret", apiSecret);

        HttpEntity<MultiValueMap<String, String>> body = new HttpEntity<>(params, headers);

        try {
            IamPortAccessToken iamPortAccessToken = restTemplate.postForObject(new URI(HOST + "/users/getToken"), body, IamPortAccessToken.class);

            return iamPortAccessToken.getResponse().getAccess_token();

        } catch (RestClientException e) {
            throw new IamPortAccessTokenException(e);

        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public String getBankHolder(String bankName, String accountNumber) {
        RestTemplate restTemplate = new RestTemplate();

        String accessToken = issueAccessToken();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(headers);

        String bankCode = bankCodes.get(bankName);

        try {
            ResponseEntity<IamPortAccountHolder> response = restTemplate.exchange(
                    HOST + "/vbanks/holder?bank_code=" + bankCode + "&bank_num=" + accountNumber,
                    HttpMethod.GET,
                    entity,
                    IamPortAccountHolder.class);

            return response.getBody().getResponse().getBank_holder();

        } catch (RestClientException e) {
            throw new IamPortBankHolderException(e);
        }
    }
}
