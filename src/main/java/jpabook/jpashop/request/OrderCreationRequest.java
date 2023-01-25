package jpabook.jpashop.request;

import jpabook.jpashop.domain.item.Book;
import lombok.Data;

@Data
public class OrderCreationRequest {

    private int count;
    private Long itemId;
    private Long memberId;
}
