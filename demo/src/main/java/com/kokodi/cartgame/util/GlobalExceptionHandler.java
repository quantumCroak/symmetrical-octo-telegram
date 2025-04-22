package com.kokodi.cartgame.util;

import com.kokodi.cartgame.util.exception.GameException;
import com.kokodi.cartgame.util.exception.GameNotFoundException;
import com.kokodi.cartgame.util.exception.GameNotInProgressException;
import com.kokodi.cartgame.util.exception.InsufficientPlayersException;
import com.kokodi.cartgame.util.exception.InvalidTargetForAttackException;
import com.kokodi.cartgame.util.exception.InvalidTurnException;
import com.kokodi.cartgame.util.exception.NotUniqueException;
import com.kokodi.cartgame.util.exception.RegisterUserException;
import com.kokodi.cartgame.util.exception.TargetUserRequiredForStealException;
import com.kokodi.cartgame.util.exception.UserNotParticipantException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RegisterUserException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse registerUserExceptionHandler(RegisterUserException ex) {
        return new ErrorResponse(ex.getMessage());
    }

    @ExceptionHandler(NotUniqueException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleNotUniqueException(NotUniqueException ex) {
        return new ErrorResponse(ex.getMessage());
    }

    @ExceptionHandler(GameNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleGameNotFoundException(GameNotFoundException ex) {
        return new ErrorResponse(ex.getMessage());
    }

    @ExceptionHandler(GameNotInProgressException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleGameNotInProgress(GameNotInProgressException ex) {
        return new ErrorResponse(ex.getMessage());
    }

    @ExceptionHandler(InvalidTurnException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleInvalidTurn(InvalidTurnException ex) {
        return new ErrorResponse(ex.getMessage());
    }

    @ExceptionHandler(UserNotParticipantException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorResponse handleUserNotParticipant(UserNotParticipantException ex) {
        return new ErrorResponse(ex.getMessage());
    }

    @ExceptionHandler(InsufficientPlayersException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleInsufficientPlayers(InsufficientPlayersException ex) {
        return new ErrorResponse(ex.getMessage());
    }

    @ExceptionHandler(TargetUserRequiredForStealException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleTargetUserRequiredForSteal(TargetUserRequiredForStealException ex) {
        return new ErrorResponse(ex.getMessage());
    }

    @ExceptionHandler(InvalidTargetForAttackException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleInvalidTargetForAttack(InvalidTargetForAttackException ex) {
        return new ErrorResponse(ex.getMessage());
    }

    @ExceptionHandler(GameException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleGameException(GameException ex) {
        return new ErrorResponse(ex.getMessage());
    }
}
