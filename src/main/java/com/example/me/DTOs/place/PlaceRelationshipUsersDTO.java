package com.example.me.DTOs.place;


import com.example.me.DTOs.UserDTO;
import com.example.me.utils.enums.UserPlaceRole;
import lombok.*;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlaceRelationshipUsersDTO {
    
    private List<UserDTO> users;
    
    private PlaceDTO place;

}



