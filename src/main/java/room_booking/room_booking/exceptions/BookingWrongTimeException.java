package room_booking.room_booking.exceptions;

public class BookingWrongTimeException extends RuntimeException{

    public BookingWrongTimeException(String message){
        super(message);
    }
}
