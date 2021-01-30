package com.ms.usermanagement.controller;


import com.ms.usermanagement.exception.UnauthorizedException;
import com.ms.usermanagement.model.User;
import com.ms.usermanagement.request.LoginRequest;
import com.ms.usermanagement.response.AuthenticationResponse;
import com.ms.usermanagement.service.CustomUserDetailsService;
import com.ms.usermanagement.util.EncryptDecrypt;
import com.ms.usermanagement.util.JwtUtil;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import java.util.UUID;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@Validated
@RequestMapping(value = "${app.url}" + "/user")
@Api(value = "Authentication System", description = "Operations pertaining to user in Authentication System")
public class UserController {

    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    // user can login here with the credentials initialized at the application startup time.
    @ApiOperation(value = "Login through the credentials you have entered while registration", response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully returns token"),
            @ApiResponse(code = 401, message = "Credentials might be invalid or token missing")
    }
    )
    @PostMapping("/login")
    public ResponseEntity generateToken(@Valid @RequestBody LoginRequest loginRequest) throws UnauthorizedException {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), EncryptDecrypt.encrypt(loginRequest.getPassword()))
            );
        } catch (BadCredentialsException ex) {
            throw new UnauthorizedException("Invalid username/password");
        }
        return ok(new AuthenticationResponse(jwtUtil.generateToken(loginRequest.getEmail())));
    }
    @ApiOperation(value = "Anyone can register here", response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully returns the User Object"),
            @ApiResponse(code = 401, message = "In case of token missing not giving token with Bearer"),
            @ApiResponse(code = 404, message = "If invalid email is entered")
    }
    )
    //user can register
    @PostMapping("/registration")
    public ResponseEntity saveUser(@Valid @RequestBody User user) {
        return new ResponseEntity(customUserDetailsService.saveUser(user), HttpStatus.OK);
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Authorization token",
                    required = true, dataType = "string", paramType = "header") })
    //user can see the list of all users

    @ApiOperation(value = "View a list of available users",response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    }
    )
    @GetMapping("/list")
    public ResponseEntity list() {
        return ok(customUserDetailsService.getAll());
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Authorization token",
                    required = true, dataType = "string", paramType = "header"),
            @ApiImplicitParam(name = "id", value = "8707fa15-c85e-4258-a7bf-2ec080992d7f",
                    required = true, dataType = "uuid", paramType = "uri param")})
    @ApiOperation(value = "delete a user by uuid")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully deleted"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity deleteUser(@PathVariable @ApiParam(value = "uuid", example = "8707fa15-c85e-4258-a7bf-2ec080992d7f") UUID id) {
        return ok(customUserDetailsService.deleteUser(id));
    }

    @ApiOperation(value = "get a user by uuid")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully returned"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    }
    )
    @GetMapping("/{id}")
    public ResponseEntity getUserById(@PathVariable @ApiParam(value = "uuid", example = "8707fa15-c85e-4258-a7bf-2ec080992d7f") UUID id) {
        return ok(customUserDetailsService.findUserById(id));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Authorization token",
                    required = true, dataType = "uuid", paramType = "header") })
    @ApiOperation(value = "Update user profile", response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully returns the Update User Object"),
            @ApiResponse(code = 401, message = "token missing or token might be without Bearer"),
            @ApiResponse(code = 404, message = "If invalid email is entered")
    }
    )
    //user can register
    @PutMapping("/update")
    public ResponseEntity updateUser(@Valid @RequestBody User user) {
        return new ResponseEntity(customUserDetailsService.updateUser(user), HttpStatus.OK);
    }

//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "Authorization", value = "Authorization token",
//                    required = true, paramType = "header") })
//    @ApiOperation(value = "Get user profile")
//    @ApiResponses(value = {
//            @ApiResponse(code = 200, message = "Successfully returns the User profile"),
//            @ApiResponse(code = 401, message = "token missing or token might be without Bearer"),
//    }
//    )
//    @RequestMapping(value="/profile", method = RequestMethod.GET, produces = "application/json")
//    @ResponseBody
//    public ResponseEntity getProfile(HttpServletRequest req){
//        String token = req.getHeader("Authorization").replace("Bearer","");
//        UserDetails userDetails=customUserDetailsService.loadUserByUsername(jwtUtil.extractUsername(token));
//        return new ResponseEntity(userDetails,HttpStatus.OK);
//    }

}
