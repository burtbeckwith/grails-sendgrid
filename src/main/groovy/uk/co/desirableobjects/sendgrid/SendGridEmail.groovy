package uk.co.desirableobjects.sendgrid

import grails.converters.JSON
import groovy.transform.CompileDynamic
import groovy.transform.CompileStatic
import groovy.transform.ToString

@CompileStatic
@ToString
class SendGridEmail {

    List<String> to = []
    List<String> toName = []
    String from
    String fromName
    String subject
    String body
    String html
    String replyTo
    Date sentDate
    String username
    String password
    List<String> bcc = []
    Map headers = [:]
    Map customHandlingInstructions = [:]
    Map<String, File> attachments = [:]

    private static final Map<String, String> allParameters = [
            username: 'api_user',
            password: 'api_key',
            to:'to',
            from:'from',
            subject:'subject',
            body:'text',
            html:'html',
            toName:'toname',
            bcc:'bcc',
            replyTo:'replyto',
            sentDate:'date',
            fromName:'fromname',
            headers:'headers',
            customHandlingInstructions:'x-smtpapi'
    ]

    Map<String, Object> toMap() {
        encodeParameters() + addAttachments()
    }

    @CompileDynamic
    private Map<String, Object> encodeParameters() {

        Map<String, Object> parameters = [:]

        allParameters.each { String internalName, String externalName ->
            def value = this[internalName]
            if (value) {
                parameters[externalName] = map(value)
            }
        }

        return parameters
    }

    private Map<String, Object> addAttachments() {

        Map<String, Object> parameters = [:]

        attachments.each { String filename, File attachment ->
            parameters["files[$filename]".toString()] = attachment.bytes
        }

        return parameters
    }

    private List<String> map(List<String> values) {
        return values
    }

    private String map(String string) {
        return string
    }

    private String map(Date date) {
        return date.format("yyyy-MM-dd'T'HH:mm:ssz")
    }

    private String map(Map map) {
        return (map as JSON).toString()
    }

    void setTo(String recipient) {
        to << recipient
    }

}
