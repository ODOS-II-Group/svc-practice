package gov.dhs.uscis.odos.service;

import org.springframework.core.io.ClassPathResource;

import gov.dhs.uscis.odos.service.dto.MailMessageDTO;

/**
 * Service Interface for Sending emails.
 */
public interface MailService {

    /**
     * Send a simple email
     *
     * @param MailMessageDTO the dto that contains the mail details
     * @return void
     */
	void sendMail(MailMessageDTO mailMessageDTO);

    /**
     * Send an email with attachment
     *
     * @param MailMessageDTO contains the mail details
     * @param ClassPathResource contains the attachment details
     * @return void
     */
	void sendMail(MailMessageDTO mailMessageDTO, ClassPathResource cpr);

}
