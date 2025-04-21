package com.kokodi.cartgame.mapper;

import com.kokodi.cartgame.model.Cartds;
import com.kokodi.cartgame.model.GameSession;
import com.kokodi.cartgame.model.User;
import com.kokodi.cartgame.model.dto.CartdsGetDTO;
import com.kokodi.cartgame.model.dto.GameSessionCreateDTO;
import com.kokodi.cartgame.model.dto.GameSessionGetDTO;
import com.kokodi.cartgame.model.dto.UserGetDTO;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-04-21T20:19:36+0300",
    comments = "version: 1.6.3, compiler: javac, environment: Java 23.0.2 (Homebrew)"
)
@Component
public class GameSessionMapperImpl implements GameSessionMapper {

    @Override
    public GameSessionCreateDTO toGetDTOFromCreate(GameSession gameSession) {
        if ( gameSession == null ) {
            return null;
        }

        GameSessionCreateDTO.GameSessionCreateDTOBuilder gameSessionCreateDTO = GameSessionCreateDTO.builder();

        gameSessionCreateDTO.statusSessionGame( gameSession.getStatusSessionGame() );

        return gameSessionCreateDTO.build();
    }

    @Override
    public GameSessionGetDTO toGetDTO(GameSession gameSession) {
        if ( gameSession == null ) {
            return null;
        }

        GameSessionGetDTO gameSessionGetDTO = new GameSessionGetDTO();

        gameSessionGetDTO.setUsers( userListToUserGetDTOList( gameSession.getUsers() ) );
        gameSessionGetDTO.setDeck( cartdsListToCartdsGetDTOList( gameSession.getDeck() ) );
        gameSessionGetDTO.setStatusSessionGame( gameSession.getStatusSessionGame() );
        Map<UUID, Integer> map = gameSession.getPlayerScores();
        if ( map != null ) {
            gameSessionGetDTO.setPlayerScores( new LinkedHashMap<UUID, Integer>( map ) );
        }

        return gameSessionGetDTO;
    }

    @Override
    public GameSession toEntity(GameSessionCreateDTO createDTO) {
        if ( createDTO == null ) {
            return null;
        }

        GameSession gameSession = new GameSession();

        gameSession.setStatusSessionGame( createDTO.getStatusSessionGame() );

        return gameSession;
    }

    protected UserGetDTO userToUserGetDTO(User user) {
        if ( user == null ) {
            return null;
        }

        UserGetDTO userGetDTO = new UserGetDTO();

        userGetDTO.setUserId( user.getUserId() );

        return userGetDTO;
    }

    protected List<UserGetDTO> userListToUserGetDTOList(List<User> list) {
        if ( list == null ) {
            return null;
        }

        List<UserGetDTO> list1 = new ArrayList<UserGetDTO>( list.size() );
        for ( User user : list ) {
            list1.add( userToUserGetDTO( user ) );
        }

        return list1;
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

    protected List<CartdsGetDTO> cartdsListToCartdsGetDTOList(List<Cartds> list) {
        if ( list == null ) {
            return null;
        }

        List<CartdsGetDTO> list1 = new ArrayList<CartdsGetDTO>( list.size() );
        for ( Cartds cartds : list ) {
            list1.add( cartdsToCartdsGetDTO( cartds ) );
        }

        return list1;
    }
}
