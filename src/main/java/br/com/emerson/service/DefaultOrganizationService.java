package br.com.emerson.service;

import java.io.InputStream;
import java.net.URI;
import java.util.List;

import javax.activation.DataHandler;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.apache.cxf.jaxrs.ext.multipart.Attachment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.emerson.model.Organization;
import br.com.emerson.repository.OrganizationRepository;
import br.com.emerson.util.ExcelRead;
import br.com.emerson.util.ExcelType;

public class DefaultOrganizationService implements OrganizationService {

    private OrganizationRepository organizationRepository;
    private static final Logger logger = LoggerFactory.getLogger(DefaultOrganizationService.class);

    @Inject
    public DefaultOrganizationService(OrganizationRepository organizationRepository) {
        this.organizationRepository = organizationRepository;
    }

    @Override
    public Response create(UriInfo uriInfo, Attachment file) {
        try {
            DataHandler handler = file.getDataHandler();
            String contentType = file.getContentType().toString();
            InputStream inputStream = handler.getInputStream();
            ExcelRead excel = new ExcelRead();

            ExcelType extension = null;
            if (contentType.equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")) {
                extension = ExcelType.XLSX;
            } else if (contentType.equals("application/vnd.ms-excel")) {
                extension = ExcelType.XLS;
            } else {
                Response.status(415).build();
            }

            List<Organization> list = excel.read(inputStream, extension);
            for (Organization organization : list) {
                organizationRepository.save(organization);
            }

            logger.debug("Organization successfully created");

            URI uri = uriInfo.getRequestUriBuilder().build();
            return Response.created(uri).entity(list).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().build();
        }
    }

}
