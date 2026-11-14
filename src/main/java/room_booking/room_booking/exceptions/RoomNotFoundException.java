package room_booking.room_booking.exceptions;

public class RoomNotFoundException extends RuntimeException{

    public RoomNotFoundException(String message){
        super(message);
    }
}