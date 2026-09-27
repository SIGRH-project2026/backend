package sn.gainde2000.backenmfpai.web.controllers.servicecarriere;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sn.gainde2000.backenmfpai.services.interfaces.servicecarriere.IAgent;

/**
 * @author bsdieme
 */
@RestController
/**@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
@RequestMapping("/agent")
@Tag(name = "gestionAgentController", description = "Permet de gérer les comptes des agents")*/
public class AgentController {

   // private final IAgent agent;

    /*@Operation(
            description = "Creation d un agent",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Agent a été créé avec succés!",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = {
                                            @ExampleObject(
                                                    value = "{\"code\" : 200, \"Status\" : \"Ok!\", \"Message\" :\"Agent Créè!\", \"payload\" :\"Agent Créè!\"}"
                                            ),
                                    }
                            )
                    )
            }
    )
    @PostMapping("/add")
    public**/

}
