package ImageHoster.controller;

import ImageHoster.model.Comment;
import ImageHoster.model.Image;
import ImageHoster.model.User;
import ImageHoster.service.CommentService;
import ImageHoster.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;

/**
 * Controller class  to implement Comments feature.
 */
@Controller
public class CommentController {

    @Autowired
    private ImageService imageService;

    @Autowired
    private CommentService commentService;

    /**
     * @param imageId
     * @param imageTitle
     * @param comment
     * @param commentToSubmit
     * @param session
     * @return String
     */
    @RequestMapping(value = "/image/{imageId}/{imageTitle}/comments", method = RequestMethod.POST)
    public String createComment(@PathVariable("imageId") Integer imageId, @PathVariable("imageTitle") String imageTitle,
                                @RequestParam("comment") String comment, Comment commentToSubmit, HttpSession session) {
        User user = (User) session.getAttribute("loggeduser");
        Image image = imageService.getImage(imageId);

        commentToSubmit.setText(comment);
        commentToSubmit.setCreatedDate(LocalDate.now());
        commentToSubmit.setImage(image);
        commentToSubmit.setUser(user);
        commentService.addComments(commentToSubmit);

        return "redirect:/images/" + imageId + "/" + image.getTitle();
    }
}