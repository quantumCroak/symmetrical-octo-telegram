package com.kokodi.cartgame.mapper;

import com.kokodi.cartgame.model.Cartds;
import com.kokodi.cartgame.model.dto.CartdsGetDTO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-04-21T20:19:36+0300",
    comments = "version: 1.6.3, compiler: javac, environment: Java 23.0.2 (Homebrew)"
)
@Component
public class CartdsMapperImpl extends CartdsMapper {

    @Override
    public List<CartdsGetDTO> toCartdsGetDTOs(List<Cartds> cards) {
        if ( cards == null ) {
            return null;
        }

        List<CartdsGetDTO> list = new ArrayList<CartdsGetDTO>( cards.size() );
        for ( Cartds cartds : cards ) {
            list.add( cartdsToCartdsGetDTO( cartds ) );
        }

        return list;
    }

    @Override
    List<Cartds> toCartdsList(List<CartdsGetDTO> cardDTOs) {
        if ( cardDTOs == null ) {
            return null;
        }

        List<Cartds> list = new ArrayList<Cartds>( cardDTOs.size() );
        for ( CartdsGetDTO cartdsGetDTO : cardDTOs ) {
            list.add( cartdsGetDTOToCartds( cartdsGetDTO ) );
        }

        return list;
    }

    protected CartdsGetDTO cartdsToCartdsGetDTO(Cartds cartds) {
        if ( cartds == null ) {
            return null;
        }

        CartdsGetDTO cartdsGetDTO = new CartdsGetDTO();

        cartdsGetDTO.setCartId( cartds.getCartId() );
        cartdsGetDTO.setValue( cartds.getValue() );
        cartdsGetDTO.setQuantity( cartds.getQuantity() );

        return cartdsGetDTO;
    }

    protected Cartds cartdsGetDTOToCartds(CartdsGetDTO cartdsGetDTO) {
        if ( cartdsGetDTO == null ) {
            return null;
        }

        Cartds cartds = new Cartds();

        cartds.setCartId( cartdsGetDTO.getCartId() );
        cartds.setValue( cartdsGetDTO.getValue() );
        cartds.setQuantity( cartdsGetDTO.getQuantity() );

        return cartds;
    }
}
