package com.del.jws.server.db;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by DodolinEL
 * date: 12.05.2017
 */
@Entity(name = "Meeting")
@Table(name = "MEETING")
public class Meeting implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "BEGIN_DATE", columnDefinition="DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date beginDate;

    @Column(name = "START_DATE", columnDefinition="DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startDate;

    @Column(name = "END_DATE", columnDefinition="DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    @JoinColumn(name = "INITIATOR_ID", nullable = false)
    private User initiator;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public User getInitiator() {
        return initiator;
    }

    public void setInitiator(User initiator) {
        this.initiator = initiator;
    }
}
