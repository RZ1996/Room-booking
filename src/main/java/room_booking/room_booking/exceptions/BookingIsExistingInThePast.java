package room_booking.room_booking.exceptions;

public class BookingIsExistingInThePast extends RuntimeException{
    public BookingIsExistingInThePast(String message){
        super(message);
    }
}
