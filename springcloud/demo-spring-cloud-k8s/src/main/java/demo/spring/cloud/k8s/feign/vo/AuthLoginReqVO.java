package demo.spring.cloud.k8s.feign.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthLoginReqVO {

    private String username;

    private String password;
}