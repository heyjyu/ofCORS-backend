package kr.heyjyu.ofcors.models;

import java.time.LocalDateTime;
import java.util.Objects;

public class KakaoPayReady {
    private String tid;

    private String next_redirect_pc_url;

    private LocalDateTime created_at;

    public KakaoPayReady() {
    }

    public KakaoPayReady(
            String tid,
            String next_redirect_pc_url,
            LocalDateTime created_at) {
        this.tid = tid;
        this.next_redirect_pc_url = next_redirect_pc_url;
        this.created_at = created_at;
    }

    public String getTid() {
        return tid;
    }

    public String getNext_redirect_pc_url() {
        return next_redirect_pc_url;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }
}
