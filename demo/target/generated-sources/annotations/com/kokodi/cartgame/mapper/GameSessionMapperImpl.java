package com.kokodi.cartgame.mapper;

import com.kokodi.cartgame.model.Cards;
import com.kokodi.cartgame.model.GameSession;
import com.kokodi.cartgame.model.User;
import com.kokodi.cartgame.model.dto.CardsGetDTO;
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
    date = "2025-04-22T02:51:54+0300",
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
        gameSessionGetDTO.setDeck( cardsListToCardsGetDTOList( gameSession.getDeck() ) );
        gameSessionGetDTO.setStatusSessionGame( gameSession.getStatusSessionGame() );
        Map<UUID, Integer> map = gameSession.getPlayerScores();
        if ( map != null ) {
            gameSessionGetDTO.setPlayerScores( new LinkedHashMap<UUID, Integer>( map ) );
        }

        return gameSessionGetDTO;
    }

    @Override
    public GameSession toEntity(GameSessionGetDTO gameSession) {
        if ( gameSession == null ) {
            return null;
        }

        GameSession gameSession1 = new GameSession();

        gameSession1.setUsers( userGetDTOListToUserList( gameSession.getUsers() ) );
        gameSession1.setStatusSessionGame( gameSession.getStatusSessionGame() );
        Map<UUID, Integer> map = gameSession.getPlayerScores();
        if ( map != null ) {
            gameSession1.setPlayerScores( new LinkedHashMap<UUID, Integer>( map ) );
        }
        gameSession1.setDeck( cardsGetDTOListToCardsList( gameSession.getDeck() ) );

        return gameSession1;
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

    protected CardsGetDTO cardsToCardsGetDTO(Cards cards) {
        if ( cards == null ) {
            return null;
        }

        CardsGetDTO cardsGetDTO = new CardsGetDTO();

        cardsGetDTO.setCardId( cards.getCardId() );
        cardsGetDTO.setValue( cards.getValue() );
        cardsGetDTO.setCardName( cards.getCardName() );
        cardsGetDTO.setQuantity( cards.getQuantity() );

        return cardsGetDTO;
    }

    protected List<CardsGetDTO> cardsListToCardsGetDTOList(List<Cards> list) {
        if ( list == null ) {
            return null;
        }

        List<CardsGetDTO> list1 = new ArrayList<CardsGetDTO>( list.size() );
        for ( Cards cards : list ) {
            list1.add( cardsToCardsGetDTO( cards ) );
        }

        return list1;
    }

    protected User userGetDTOToUser(UserGetDTO userGetDTO) {
        if ( userGetDTO == null ) {
            return null;
        }

        User.UserBuilder user = User.builder();

        user.userId( userGetDTO.getUserId() );

        return user.build();
    }

    protected List<User> userGetDTOListToUserList(List<UserGetDTO> list) {
        if ( list == null ) {
            return null;
        }

        List<User> list1 = new ArrayList<User>( list.size() );
        for ( UserGetDTO userGetDTO : list ) {
            list1.add( userGetDTOToUser( userGetDTO ) );
        }

        return list1;
    }

    protected Cards cardsGetDTOToCards(CardsGetDTO cardsGetDTO) {
        if ( cardsGetDTO == null ) {
            return null;
        }

        Cards cards = new Cards();

        cards.setCardId( cardsGetDTO.getCardId() );
        cards.setValue( cardsGetDTO.getValue() );
        cards.setCardName( cardsGetDTO.getCardName() );
        cards.setQuantity( cardsGetDTO.getQuantity() );

        return cards;
    }

    protected List<Cards> cardsGetDTOListToCardsList(List<CardsGetDTO> list) {
        if ( list == null ) {
            return null;
        }

        List<Cards> list1 = new ArrayList<Cards>( list.size() );
        for ( CardsGetDTO cardsGetDTO : list ) {
            list1.add( cardsGetDTOToCards( cardsGetDTO ) );
        }

        return list1;
    }
}
