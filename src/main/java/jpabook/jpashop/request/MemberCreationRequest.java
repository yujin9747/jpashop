package jpabook.jpashop.request;

import jpabook.jpashop.domain.Address;
import lombok.Data;

@Data
public class MemberCreationRequest {

    private String name;
    private Address address;
}
