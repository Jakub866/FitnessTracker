package com.capgemini.wsb.fitnesstracker.mail.api;

import java.util.Objects;

/**
 * Data Transfer Object for email messages.
 */
public class EmailDto {
    private final String recipient;
    private final String subject;
    private final String body;

    public EmailDto(String recipient, String subject, String body) {
        this.recipient = recipient;
        this.subject = subject;
        this.body = body;
    }

    public String getRecipient() {
        return recipient;
    }

    public String getSubject() {
        return subject;
    }

    public String getBody() {
        return body;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmailDto emailDto = (EmailDto) o;
        return Objects.equals(recipient, emailDto.recipient) &&
                Objects.equals(subject, emailDto.subject) &&
                Objects.equals(body, emailDto.body);
    }

    @Override
    public int hashCode() {
        return Objects.hash(recipient, subject, body);
    }

    @Override
    public String toString() {
        return "EmailDto{" +
                "recipient='" + recipient + '\'' +
                ", subject='" + subject + '\'' +
                ", body='" + body + '\'' +
                '}';
    }
}
