package com.pixeltrice.springbootimportcsvfileapp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@CrossOrigin("http://localhost:8080")
@Controller
@RequestMapping("/api/csv")
public class CSVController {

  @Autowired
  CSVService fileService;

  @PostMapping("/upload")
  public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file) {
    String message = "";

    if (CSVHelper.hasCSVFormat(file)) {
      try {
        fileService.save(file);

        message = "Uploaded the file successfully: " + file.getOriginalFilename();

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/csv/download/")
                .path(file.getOriginalFilename())
                .toUriString();

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message,fileDownloadUri));
      } catch (Exception e) {
        message = "Could not upload the file: " + file.getOriginalFilename() + "!";
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message,""));
      }
    }

    message = "Please upload a csv file!";
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message,""));
  }

  @GetMapping("/tutorials")
  public ResponseEntity<List<DeveloperTutorial>> getAllTutorials() {
    try {
      List<DeveloperTutorial> tutorials = fileService.getAllTutorials();

      if (tutorials.isEmpty()) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      }

      return new ResponseEntity<>(tutorials, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping("/download/{fileName:.+}")
  public ResponseEntity<Resource> downloadFile(@PathVariable String fileName) {
    InputStreamResource file = new InputStreamResource(fileService.load());

    return ResponseEntity.ok()
        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName)
        .contentType(MediaType.parseMediaType("application/csv"))
        .body(file);
  }

//  @RequestMapping(method = RequestMethod.GET, produces = "application/json", consumes = "application/json")
  @GetMapping("/user")
  public ResponseEntity getUserProfile(@RequestBody UserProfileRequest request) {
//    log.info("request : {}", request.toString());
    try {
      UserProfileResponse response = fileService.getUserProfileFromId(request.getEmail());
      GenericResponse gr = new GenericResponse(200, "success", null);
      gr.setData(response);
      return ResponseEntity.ok().body(gr);
//    } catch (GenericException e) {
//      GenericResponse gr = new GenericResponse(e.getResponse());
//      return ResponseEntity.status(gr.getCode()).body(gr);
    } catch (Exception e) {
//      log.error("User Service for request failed: {}", Arrays.toString(e.getStackTrace()));
//      throw new GenericException(e);
      throw e;
    }
  }

//  @RequestMapping(method = RequestMethod.PUT, value = "/invest", produces = "application/json", consumes = "application/json")
  @PutMapping("/invest")
  public ResponseEntity getAddUserInvestment(@RequestBody UserInvestmentRequest request) {
//    log.info("request : {}", request.toString());
    try {
      boolean response = fileService.addUserInvestment(request.getEmail(), request.getStocks());
      GenericResponse gr = new GenericResponse(200, "success", null);
      gr.setData(response);
      return ResponseEntity.ok().body(gr);
//    } catch (GenericException e) {
//      GenericResponse gr = new GenericResponse(e.getResponse());
//      return ResponseEntity.status(gr.getCode()).body(gr);
    } catch (Exception e) {
//      log.error("User Service for request failed: {}", Arrays.toString(e.getStackTrace()));
//      throw new GenericException(e);
      throw e;
    }
  }
}
