package fontys.ict.kwetter.KwetterAccountService.controllers;

import fontys.ict.kwetter.KwetterAccountService.models.Follow;
import fontys.ict.kwetter.KwetterAccountService.repositories.FollowRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:4200","http://localhost:8081","http://localhost:8082","http://localhost:8083"})
@RequestMapping(value = "/follow")
public class FollowController {
    private final FollowRepository followRepository;

    public FollowController(FollowRepository followRepository) {
        this.followRepository = followRepository;
    }

    @RequestMapping(value = "/followers/{accountId}", method = RequestMethod.GET)
    public @ResponseBody
    List<Follow> getFollowers(@PathVariable("accountId") Long accountId) {
        return followRepository.getAllByFollowingAccount_Id(accountId);
    }

    @RequestMapping(value = "/following/{accountId}", method = RequestMethod.GET)
    public @ResponseBody
    List<Follow> getFollowing(@PathVariable("accountId") Long accountId) {
        return followRepository.getAllByAccount_Id(accountId);
    }

    @RequestMapping(method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public @ResponseBody Follow follow(@RequestBody Follow follow){
        return followRepository.save(follow);
    }

    @RequestMapping(method = RequestMethod.DELETE, consumes = "application/json")
    public @ResponseBody void unfollow(@RequestBody Follow follow){
        followRepository.delete(follow);
    }
}
