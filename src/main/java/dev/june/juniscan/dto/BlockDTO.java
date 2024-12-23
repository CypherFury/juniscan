/**
 * Data Transfer Object for a blockchain block.
 * Contains the header, extrinsics, and justifications.
 *
 * @author cypherfury
 */
package dev.june.juniscan.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BlockDTO {

    private HeaderDTO header;
    private List<String> extrinsics;

}

