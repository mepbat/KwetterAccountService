package fontys.ict.kwetter.KwetterAccountService.controllers;

import fontys.ict.kwetter.KwetterAccountService.models.Follow;
import fontys.ict.kwetter.KwetterAccountService.repositories.FollowRepository;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/follow")
public class FollowController {
    private final FollowRepository followRepository;
    private final AmqpTemplate rabbitTemplate;

    @Value("${rabbitmq.exchange}")
    private String exchange;
    @Value("${rabbitmq.routingKey}")
    private String routingkey;


    public FollowController(FollowRepository followRepository, AmqpTemplate rabbitTemplate) {
        this.followRepository = followRepository;
        this.rabbitTemplate = rabbitTemplate;
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
