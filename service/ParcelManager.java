package service;

import exception.DuplicateTrackingException;
import exception.ParcelNotFoundException;
import model.Parcel;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public class ParcelManager {
    private static ParcelManager instance;
    private final Map<String, Parcel> parcels = new LinkedHashMap<>();

    private ParcelManager(){}

    public static ParcelManager getInstance() {
        if (instance == null) {
            instance = new ParcelManager();
        }
        return instance;
    }

    public boolean keyExists(String trackingNumber){
        return parcels.containsKey(trackingNumber);
    }

    public void addParcel(Parcel parcel) throws DuplicateTrackingException {
        if(keyExists(parcel.getTrackingNumber()))
            throw new DuplicateTrackingException("Tracking number already exists!");
        parcels.put(parcel.getTrackingNumber(), parcel);
    }

    public Parcel findParcel(String trackingNumber) throws ParcelNotFoundException {
        Parcel parcel = parcels.get(trackingNumber);
        if (parcel == null)
            throw new ParcelNotFoundException("Parcel not found!");
        return parcel;
    }

    public Collection<Parcel> getAll(){
        return parcels.values();
    }
}