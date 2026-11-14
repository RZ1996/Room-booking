package room_booking.room_booking.exceptions;

public class RoomNotActiveException extends RuntimeException{

    public RoomNotActiveException(String message){
        super(message);
    }
}