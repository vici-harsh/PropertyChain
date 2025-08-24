package com.propertychain.admin.model;

import lombok.*;
import java.time.Instant;
import java.util.List;

// Force String output for Instant via getter
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Property {
    private Long id;
    private String addressLine;
    private String city;
    private String state;
    private String postalCode;

    private String description;
    private Long price;            // CAD
    private String propertyType;   // Apartment | House | Condo | Townhouse | Villa | Studio
    private Integer bedrooms;
    private Integer bathrooms;
    private Integer areaSqft;

    private List<String> images;   // URLs
    private String ownerWallet;    // 0x...

    private Double lat;
    private Double lng;

    private Instant createdAt;

    // ---- explicit getters/setters (keep your ones; shown for clarity) ----
    public String getAddressLine() { return addressLine; }
    public void setAddressLine(String v) { this.addressLine = v; }
    public Long getId() { return id; }
    public void setId(Long v) { this.id = v; }
    public String getCity() { return city; }
    public void setCity(String v) { this.city = v; }
    public String getState() { return state; }
    public void setState(String v) { this.state = v; }
    public String getPostalCode() { return postalCode; }
    public void setPostalCode(String v) { this.postalCode = v; }
    public String getDescription() { return description; }
    public void setDescription(String v) { this.description = v; }
    public Long getPrice() { return price; }
    public void setPrice(Long v) { this.price = v; }
    public String getPropertyType() { return propertyType; }
    public void setPropertyType(String v) { this.propertyType = v; }
    public Integer getBedrooms() { return bedrooms; }
    public void setBedrooms(Integer v) { this.bedrooms = v; }
    public Integer getBathrooms() { return bathrooms; }
    public void setBathrooms(Integer v) { this.bathrooms = v; }
    public Integer getAreaSqft() { return areaSqft; }
    public void setAreaSqft(Integer v) { this.areaSqft = v; }
    public List<String> getImages() { return images; }
    public void setImages(List<String> v) { this.images = v; }
    public String getOwnerWallet() { return ownerWallet; }
    public void setOwnerWallet(String v) { this.ownerWallet = v; }
    public Double getLat() { return lat; }
    public void setLat(Double v) { this.lat = v; }
    public Double getLng() { return lng; }
    public void setLng(Double v) { this.lng = v; }

    // ⭐ CRUCIAL: annotate the GETTER so Jackson always writes a String
    @JsonSerialize(using = ToStringSerializer.class)
    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant v) { this.createdAt = v; }
}
