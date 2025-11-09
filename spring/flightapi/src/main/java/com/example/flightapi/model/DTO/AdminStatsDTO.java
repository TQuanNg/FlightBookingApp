package com.example.flightapi.model.DTO;

public class AdminStatsDTO {
    private long totalUsers;
    private long totalBookings;
    private long totalFlights;

    public AdminStatsDTO(long totalUsers, long totalBookings, long totalFlights) {
        this.totalUsers = totalUsers;
        this.totalBookings = totalBookings;
        this.totalFlights = totalFlights;
    }

    public long getTotalUsers() {
        return totalUsers;
    }

    public long getTotalBookings() {
        return totalBookings;
    }

    public long getTotalFlights() {
        return totalFlights;
    }
}

