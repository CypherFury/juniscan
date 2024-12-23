/**
 * Data Transfer Object for the header of a blockchain block.
 * Contains metadata such as the parent block hash, block number,
 * state root, extrinsics root, and the block digest.
 *
 * @author cypherfury
 */
package dev.june.juniscan.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class HeaderDTO {

    private String parentHash;
    private String number;
    private String stateRoot;
    private String extrinsicsRoot;
    private DigestDTO digest;

}
