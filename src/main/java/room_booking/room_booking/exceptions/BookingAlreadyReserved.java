package room_booking.room_booking.exceptions;

public class BookingAlreadyReserved extends RuntimeException{

    public BookingAlreadyReserved(String message){
        super(message);
    }
}
