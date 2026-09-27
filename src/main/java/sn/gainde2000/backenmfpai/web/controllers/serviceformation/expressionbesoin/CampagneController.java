package sn.gainde2000.backenmfpai.web.controllers.serviceformation.expressionbesoin;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.poi.ss.usermodel.Font;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import sn.gainde2000.backenmfpai.entities.serviceformation.expressionbession.Campagne;
import sn.gainde2000.backenmfpai.entities.serviceformation.expressionbession.ExpressionDeBesoin;
import sn.gainde2000.backenmfpai.repositories.serviceformation.expressionbesoin.CampagneRepository;
import sn.gainde2000.backenmfpai.repositories.serviceformation.expressionbesoin.ExpressionDeBesoinRepository;
import sn.gainde2000.backenmfpai.repositories.serviceformation.expressionbesoin.TraitementExpressionDeBesoinRepository;
import sn.gainde2000.backenmfpai.services.interfaces.serviceformation.expressionbesoin.ICampagne;
import sn.gainde2000.backenmfpai.web.dtos.requests.serviceformation.expressionbesoin.CampagneRequestDTO;
import sn.gainde2000.backenmfpai.web.dtos.responses.Response;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.time.LocalDate;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
@RequestMapping("/campagne")
@Tag(name = "GestionCampagneController", description = "Permet de gérer les campagnes")
public class CampagneController {
    private final ICampagne iCampagne;
  private final ExpressionDeBesoinRepository expressionDeBesoinRepository;
  private final CampagneRepository campagneRepository;
  private final TraitementExpressionDeBesoinRepository traitementExpressionDeBesoinRepository;

  @Operation(description = "Création campagne")
    @PostMapping("/add")
    // @PreAuthorize("hasAnyAuthority('Chef-division','Chef-Bureau')")
    public ResponseEntity<Response<Object>> saveCampagne(@RequestBody CampagneRequestDTO campagneRequestDTO,
            HttpServletRequest request) {
        return ResponseEntity.ok().body(iCampagne.saveCampagne(campagneRequestDTO, request));
    }

    @Operation(description = "Modification campagne")
    @PostMapping("/edit/{id}")
    // @PreAuthorize("hasAnyAuthority('Chef-division','Chef-bureau')")
    public ResponseEntity<Response<Object>> editCampagne(@RequestBody CampagneRequestDTO campagneRequestDTO,
            @PathVariable long id) {
        return ResponseEntity.ok().body(iCampagne.editCampagne(campagneRequestDTO, id));
    }

    @Operation(description = "Recupèration campagne")
    @GetMapping("/get/{id}")
    // @PreAuthorize("hasAnyAuthority('Chef-division','Chef-bureau')")
    public ResponseEntity<Response<Object>> getCampagne(@PathVariable long id) {
        return ResponseEntity.ok().body(iCampagne.getCampagne(id));
    }

    @Operation(description = "Suppression campagne")
    @DeleteMapping("/delete/{id}")
    // @PreAuthorize("hasAnyAuthority('Chef-division','Chef-bureau')")
    public ResponseEntity<Response<Object>> deleteCampagne(@PathVariable long id) {
        return ResponseEntity.ok().body(iCampagne.deleteCampagne(id));
    }

    @Operation(description = "Listes campagnes")
    @GetMapping("/all")
    // @PreAuthorize("hasAnyAuthority('Chef-division','Chef-bureau','Directeur-DRH','Chef-EFF','Chef-etablissement','Representant-IA','Representant-IEF')")
    public ResponseEntity<Response<Object>> getAllCampagne(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size,
            @RequestParam(name = "filter", defaultValue = "") String filter,
            @RequestParam(name = "nom", defaultValue = "") String nom,
            @RequestParam(name = "dateDebut", defaultValue = "") String dateDebut,
            @RequestParam(name = "dateFin", defaultValue = "") String dateFin) {
        return ResponseEntity.ok().body(iCampagne.getAllCampagne(page, size, filter, nom, dateDebut, dateFin));
    }

    @Operation(description = "demarrée une campagne")
    @GetMapping("/start/{id}")
    // @PreAuthorize("hasAnyAuthority('Chef-division','Chef-bureau','Directeur-DRH','Chef-EFF','Chef-etablissement','Representant-IA','Representant-IEF')")
    public ResponseEntity<Response<Object>> startCampagne(@PathVariable long id, HttpServletRequest request) {
        return ResponseEntity.ok().body(iCampagne.startCampagne(id, request));
    }

    @Operation(description = "Clôturée une campagne")
    @GetMapping("/stop/{id}")
    // @PreAuthorize("hasAnyAuthority('Chef-division','Chef-bureau','Directeur-DRH','Chef-EFF','Chef-etablissement','Representant-IA','Representant-IEF')")
    public ResponseEntity<Response<Object>> stopCampagne(@PathVariable long id, HttpServletRequest request) {
        return ResponseEntity.ok().body(iCampagne.stopCampagne(id, request));
    }

    @Operation(description = "Listes expressions de besoin par campagnes")
    @GetMapping("/expressionDeBesoin/{id}")
    // @PreAuthorize("hasAnyAuthority('Chef-division','Chef-bureau','Directeur-DRH','Chef-EFF','Chef-etablissement','Representant-IA','Representant-IEF')")
    public ResponseEntity<Response<Object>> getExpressionDeBesoinByCampagne(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size,
            @RequestParam(name = "statut", defaultValue = "") String statut,
            @RequestParam(name = "besoin", defaultValue = "") String besoin,
            @RequestParam(name = "prenomDemandeur", defaultValue = "") String prenomDemandeur,
            @RequestParam(name = "nomDemandeur", defaultValue = "") String nomDemandeur,
            @RequestParam(name = "date", defaultValue = "") String date,
            @RequestParam(name = "filter", defaultValue = "") String filter, @PathVariable long id) {
        return ResponseEntity.ok().body(iCampagne.getExpressionDeBesoinByCampagne(page, size, filter, statut, besoin,
                prenomDemandeur, nomDemandeur, date, id));
    }

  @GetMapping("/generateExcel/{id}")
  public ResponseEntity<byte[]> generateExcelWithImageAndData(@PathVariable Long id) throws IOException {
    Optional<Campagne> campagneOptional = campagneRepository.findByIdAndDeletedFalse(id);
    if (campagneOptional.isEmpty()) {
      return ResponseEntity.notFound().build();
    }
    List<ExpressionDeBesoin> expressionDeBesoinList = expressionDeBesoinRepository.findByCampagne_IdAndDeletedFalse(id);

    Workbook workbook = new XSSFWorkbook();
    Sheet sheet = workbook.createSheet("Sheet1");
  try {

    ClassPathResource resource = new ClassPathResource("img.svg");
    InputStream inputStream = resource.getInputStream();
    byte[] bytes = IOUtils.toByteArray(inputStream);
    int pictureIdx = workbook.addPicture(bytes, Workbook.PICTURE_TYPE_PNG);
    CreationHelper helper = workbook.getCreationHelper();
    Drawing<?> drawing = sheet.createDrawingPatriarch();
    ClientAnchor anchor = helper.createClientAnchor();
    // Set the image position to not resize (set to original size)
    anchor.setAnchorType(ClientAnchor.AnchorType.MOVE_AND_RESIZE);
    anchor.setCol1(0); // Column index where you want to insert the image
    anchor.setRow1(0); // Row index where you want to insert the image
    Picture picture = drawing.createPicture(anchor, pictureIdx);
    picture.resize(); // To ensure the picture retains its original size
    inputStream.close();
  }catch (Exception e) {
    e.printStackTrace();
  }




    // Skip 5 rows starting from row 2
    for (int i = 0; i < 5; i++) {
      sheet.createRow(i + 1);
    }

    Row titleRow_1 = sheet.createRow(0); // Row 7
    Cell titleCell_0 = titleRow_1.createCell(0);
    Cell titleCell_1 = titleRow_1.createCell(1);
    titleCell_0.setCellValue("Campagne   : ".toUpperCase()+campagneOptional.get().getNom());
//    titleCell_1.setCellValue();

    Row titleRow_2 = sheet.createRow(1); // Row 7
    Cell titleCell_00 = titleRow_2.createCell(0);
    Cell titleCell_11 = titleRow_2.createCell(1);
    titleCell_00.setCellValue("Date début : ".toUpperCase()+campagneOptional.get().getDateDebut().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
//    titleCell_11.setCellValue();

    Row titleRow_3 = sheet.createRow(2); // Row 7
    Cell titleCell_000 = titleRow_3.createCell(0);
    Cell titleCell_110 = titleRow_3.createCell(1);
    titleCell_000.setCellValue("Date Fin   : ".toUpperCase()+campagneOptional.get().getDateFin().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
//    titleCell_110.setCellValue();

    Row titleRow_4 = sheet.createRow(4); // Row 7
    Cell titleCell_0000 = titleRow_4.createCell(0);
    CellStyle titleCellStyle = workbook.createCellStyle();
    Font titleFont = workbook.createFont();
    titleFont.setFontHeightInPoints((short) 14); // Set font size to 14
    titleFont.setBold(true);
    titleCellStyle.setFont(titleFont);
    titleCell_0000.setCellValue("Liste des expressions de besoin en formation continue".toUpperCase(Locale.ROOT));
    titleCell_0000.setCellStyle(titleCellStyle);

    CellStyle otherStyle = workbook.createCellStyle();
    Font other = workbook.createFont();
    other.setFontHeightInPoints((short) 14); // Set font size to 14
    other.setBold(true);
    otherStyle.setFont(titleFont);




    ArrayList<String> headers = new ArrayList<>(Arrays.asList("##","Référence EB".toUpperCase(), "Demandeur (Entité)".toUpperCase(), "Besoins".toUpperCase(),"Theme provisoir".toUpperCase(),"Date de soumission".toUpperCase(),"Date de traitement".toUpperCase(), "Statut".toUpperCase()));
    int rowNum = 6;
    Row row = sheet.createRow(rowNum);

    for (int i = 0; i < headers.size(); i++) {
      row.createCell(i).setCellValue(headers.get(i));
//      row.setRowStyle(titleCellStyle);
    }
    rowNum++;
    int counter = 0;
    for (ExpressionDeBesoin expression : expressionDeBesoinList) {
      Row row_ = sheet.createRow(rowNum++);
      row_.createCell(0).setCellValue(++counter+"");
      row_.createCell(1).setCellValue(expression.getReference());
      row_.createCell(2).setCellValue(expression.getUtilisateur().getPrenom()+" "+expression.getUtilisateur().getNom());
      row_.createCell(3).setCellValue(expression.getBesoin());
      row_.createCell(4).setCellValue(traitementExpressionDeBesoinRepository.findTraitementExpressionDeBesoinByExpressionDeBesoin_IdAndActivatedTrue(expression.getId()).get(0).getThemeProvisoire() == null? "<---->" : traitementExpressionDeBesoinRepository.findTraitementExpressionDeBesoinByExpressionDeBesoin_IdAndActivatedTrue(expression.getId()).get(0).getThemeProvisoire());
      row_.createCell(5).setCellValue(expression.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
      row_.createCell(6).setCellValue((traitementExpressionDeBesoinRepository.findTraitementExpressionDeBesoinByExpressionDeBesoin_IdAndActivatedTrue(expression.getId()).get(0).getDateTraitement() != null && expression.getStatutExpression().name().equals("TRAITER")) ? traitementExpressionDeBesoinRepository.findTraitementExpressionDeBesoinByExpressionDeBesoin_IdAndActivatedTrue(expression.getId()).get(0).getDateTraitement().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")):  "<---->");
      row_.createCell(7).setCellValue(expression.getStatutExpression().name().equals("TRAITER") ? "Traitée" : "Non Traitée");
      row_.setRowStyle(otherStyle);
    }

    // Resize all columns to fit the content size
    for (int i = 1; i < expressionDeBesoinList.size(); i++) {
      sheet.autoSizeColumn(i);
    }


    // Generate Excel file
    ByteArrayOutputStream excelOutputStream = new ByteArrayOutputStream();
    workbook.write(excelOutputStream);
    workbook.close();

    // Set response headers
    HttpHeaders headers_ = new HttpHeaders();
    headers_.setContentType(MediaType.APPLICATION_OCTET_STREAM);
    headers_.setContentDispositionFormData("filename", "excel_with_image_and_data.xlsx");

    // Return Excel file as response
    return new ResponseEntity<>(excelOutputStream.toByteArray(), headers_, HttpStatus.OK);
  }

  private BufferedImage resizeImage(BufferedImage originalImage, int width, int height) {
    BufferedImage resizedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    resizedImage.createGraphics().drawImage(originalImage, 0, 0, width, height, null);
    return resizedImage;
  }
    }



//    @GetMapping("/export-excel/{idCampagne}")
//    public ResponseEntity<Response<Object>> exportExcel(@PathVariable long idCampagne, @RequestBody ) {
//
//    }


