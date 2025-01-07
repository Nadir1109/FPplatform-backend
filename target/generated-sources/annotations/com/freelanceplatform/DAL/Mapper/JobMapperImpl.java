package com.freelanceplatform.DAL.Mapper;

import com.freelanceplatform.DAL.Entity.Job;
import com.freelanceplatform.DAL.Entity.User;
import com.freelanceplatform.DTO.CreateJobDTO;
import com.freelanceplatform.DTO.EditJobDTO;
import com.freelanceplatform.DTO.JobDTO;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoField;
import javax.annotation.processing.Generated;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-01-07T20:48:02+0100",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 23 (Oracle Corporation)"
)
@Component
public class JobMapperImpl implements JobMapper {

    private final DatatypeFactory datatypeFactory;

    public JobMapperImpl() {
        try {
            datatypeFactory = DatatypeFactory.newInstance();
        }
        catch ( DatatypeConfigurationException ex ) {
            throw new RuntimeException( ex );
        }
    }

    @Override
    public JobDTO toDTO(Job job) {
        if ( job == null ) {
            return null;
        }

        JobDTO jobDTO = new JobDTO();

        jobDTO.setUserName( jobUserName( job ) );
        jobDTO.setUserEmail( jobUserEmail( job ) );
        jobDTO.setId( job.getId() );
        jobDTO.setTitle( job.getTitle() );
        jobDTO.setDescription( job.getDescription() );
        jobDTO.setBudget( job.getBudget() );
        jobDTO.setDeadline( xmlGregorianCalendarToLocalDate( localDateTimeToXmlGregorianCalendar( job.getDeadline() ) ) );

        return jobDTO;
    }

    @Override
    public Job toEntity(CreateJobDTO createJobDTO) {
        if ( createJobDTO == null ) {
            return null;
        }

        Job job = new Job();

        job.setTitle( createJobDTO.getTitle() );
        job.setDescription( createJobDTO.getDescription() );
        job.setBudget( createJobDTO.getBudget() );
        job.setDeadline( xmlGregorianCalendarToLocalDateTime( localDateToXmlGregorianCalendar( createJobDTO.getDeadline() ) ) );

        return job;
    }

    @Override
    public void updateEntityFromDTO(EditJobDTO editJobDTO, Job job) {
        if ( editJobDTO == null ) {
            return;
        }

        job.setTitle( editJobDTO.getTitle() );
        job.setDescription( editJobDTO.getDescription() );
        job.setBudget( editJobDTO.getBudget() );
        job.setDeadline( xmlGregorianCalendarToLocalDateTime( localDateToXmlGregorianCalendar( editJobDTO.getDeadline() ) ) );
    }

    private XMLGregorianCalendar localDateToXmlGregorianCalendar( LocalDate localDate ) {
        if ( localDate == null ) {
            return null;
        }

        return datatypeFactory.newXMLGregorianCalendarDate(
            localDate.getYear(),
            localDate.getMonthValue(),
            localDate.getDayOfMonth(),
            DatatypeConstants.FIELD_UNDEFINED );
    }

    private XMLGregorianCalendar localDateTimeToXmlGregorianCalendar( LocalDateTime localDateTime ) {
        if ( localDateTime == null ) {
            return null;
        }

        return datatypeFactory.newXMLGregorianCalendar(
            localDateTime.getYear(),
            localDateTime.getMonthValue(),
            localDateTime.getDayOfMonth(),
            localDateTime.getHour(),
            localDateTime.getMinute(),
            localDateTime.getSecond(),
            localDateTime.get( ChronoField.MILLI_OF_SECOND ),
            DatatypeConstants.FIELD_UNDEFINED );
    }

    private static LocalDate xmlGregorianCalendarToLocalDate( XMLGregorianCalendar xcal ) {
        if ( xcal == null ) {
            return null;
        }

        return LocalDate.of( xcal.getYear(), xcal.getMonth(), xcal.getDay() );
    }

    private static LocalDateTime xmlGregorianCalendarToLocalDateTime( XMLGregorianCalendar xcal ) {
        if ( xcal == null ) {
            return null;
        }

        if ( xcal.getYear() != DatatypeConstants.FIELD_UNDEFINED
            && xcal.getMonth() != DatatypeConstants.FIELD_UNDEFINED
            && xcal.getDay() != DatatypeConstants.FIELD_UNDEFINED
            && xcal.getHour() != DatatypeConstants.FIELD_UNDEFINED
            && xcal.getMinute() != DatatypeConstants.FIELD_UNDEFINED
        ) {
            if ( xcal.getSecond() != DatatypeConstants.FIELD_UNDEFINED
                && xcal.getMillisecond() != DatatypeConstants.FIELD_UNDEFINED ) {
                return LocalDateTime.of(
                    xcal.getYear(),
                    xcal.getMonth(),
                    xcal.getDay(),
                    xcal.getHour(),
                    xcal.getMinute(),
                    xcal.getSecond(),
                    Duration.ofMillis( xcal.getMillisecond() ).getNano()
                );
            }
            else if ( xcal.getSecond() != DatatypeConstants.FIELD_UNDEFINED ) {
                return LocalDateTime.of(
                    xcal.getYear(),
                    xcal.getMonth(),
                    xcal.getDay(),
                    xcal.getHour(),
                    xcal.getMinute(),
                    xcal.getSecond()
                );
            }
            else {
                return LocalDateTime.of(
                    xcal.getYear(),
                    xcal.getMonth(),
                    xcal.getDay(),
                    xcal.getHour(),
                    xcal.getMinute()
                );
            }
        }
        return null;
    }

    private String jobUserName(Job job) {
        if ( job == null ) {
            return null;
        }
        User user = job.getUser();
        if ( user == null ) {
            return null;
        }
        String name = user.getName();
        if ( name == null ) {
            return null;
        }
        return name;
    }

    private String jobUserEmail(Job job) {
        if ( job == null ) {
            return null;
        }
        User user = job.getUser();
        if ( user == null ) {
            return null;
        }
        String email = user.getEmail();
        if ( email == null ) {
            return null;
        }
        return email;
    }
}
