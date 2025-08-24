package com.propertychain.admin.model;

import java.time.Instant;

public class HistoryEvent {
    private String type;     // ADDED | TRANSFER | ESCROW_CREATED | ...
    private String from;
    private String to;
    private String tx;
    private Long   block;
    private Instant at;
    private String details;

    public HistoryEvent() {}

    public HistoryEvent(String type, String from, String to, String tx, Long block, Instant at, String details) {
        this.type = type;
        this.from = from;
        this.to = to;
        this.tx = tx;
        this.block = block;
        this.at = at;
        this.details = details;
    }

    // getters
    public String getType()      { return type; }
    public String getFrom()      { return from; }
    public String getTo()        { return to; }
    public String getTx()        { return tx; }
    public Long   getBlock()     { return block; }
    public Instant getAt()       { return at; }
    public String getDetails()   { return details; }

    // setters
    public void setType(String type)        { this.type = type; }
    public void setFrom(String from)        { this.from = from; }
    public void setTo(String to)            { this.to = to; }
    public void setTx(String tx)            { this.tx = tx; }
    public void setBlock(Long block)        { this.block = block; }
    public void setAt(Instant at)           { this.at = at; }
    public void setDetails(String details)  { this.details = details; }
}
