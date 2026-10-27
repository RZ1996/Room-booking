package room_booking.room_booking.exceptions;

public class BookingNotFoundException extends RuntimeException{

    public BookingNotFoundException(String message){
        super(message);
    }
}
