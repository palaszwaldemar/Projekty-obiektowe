package hotelService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class HotelService {
    private final Hotel hotel = new Hotel();

    List<Room> allRooms() {
        return hotel.getAllRooms();
    }

    List<Room> availableRooms() {
        return hotel.getAvailableRooms();
    }

    List<Room> unavailableRooms() {
        return hotel.getUnavailableRooms();
    }

    List<Room> dirtyRooms() {
        return hotel.getDirtyRooms();
    }

    String cleanRoom(int numberOfRoom) {
        Room roomToClean = hotel.getRoom(numberOfRoom);
        if (roomToClean == null) { // TODO: 10.08.2022 przetestowac wersje z wyjatkami
            return "Nie ma takiego pokoju";
        }
        if (roomToClean.isClean()) {
            return "Pokój czysy / zajęty przez gościa hotelowego";
        }
        roomToClean.setAvailable(true);
        roomToClean.setClean(true);
        return "Pokój został wysprzątany";
    }

    String bookRoom(int numberOfRoom, List<Guest> guests, LocalDate checkOutDate) {
        Room roomToBook = hotel.getRoom(numberOfRoom);
        if (roomToBook == null) {
            return "Nie ma takiego pokoju";
        }
        if (!someoneIsAdult(guests)) {
            return "Nie można się zameldować. Przynajmniej jedna osoba powinna być pełnoletnia";
        }
        if (roomToBook.isAvailable()) {
            roomToBook.setAvailable(false);
            roomToBook.setGuests(guests);
            roomToBook.setCheckInDate(LocalDate.now());
            roomToBook.setCheckOutDate(checkOutDate);
            return "Zostałeś zameldwany w pokoju nr " + numberOfRoom;
        }
        return "Nie można zarezerwować tego pokoju (zarezerwowany lub niegotowy)";
    }

    boolean enoughPlaces(int numberOfGuest, int numberOfRoom) {
        return numberOfGuest <= hotel.getRoom(numberOfRoom).getHowManyPersonCanCheckIn();
    }

    private boolean someoneIsAdult(List<Guest> guests) {
        List<Guest> adultGuests = new ArrayList<>();
        for (Guest guest : guests) {
            if (guest.isAdult()) {
                adultGuests.add(guest);
            }
        }
        return !adultGuests.isEmpty();
    }

    String unBookRoom(int numberOfRoom) {
        Room roomToUnbook = hotel.getRoom(numberOfRoom);
        if (roomToUnbook == null) {
            return "Nie ma takiego pokoju";
        }
        if (!roomToUnbook.isAvailable() && roomToUnbook.isClean()) {
            roomToUnbook.setClean(false);
            return "Zostaleś wymeldowany z pokoju nr " + numberOfRoom;
        }
        return "Nie można wymeldować się z tego pokoju, ponieważ nie był on zajęty";

    }
}
