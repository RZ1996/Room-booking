package room_booking.room_booking.exceptions;

public class BookingAlreadyCancelled extends RuntimeException{

    public BookingAlreadyCancelled(String message){
        super(message);
    }
}
