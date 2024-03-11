package org.garden.com.converter;

public interface Converter<Order, Dto> {

    Dto toDto(Order order);

    Order toOrder(Dto dto);
}