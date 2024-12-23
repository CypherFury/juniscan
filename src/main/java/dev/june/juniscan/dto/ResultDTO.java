/**
 * Data Transfer Object representing the result of a blockchain RPC response.
 * Encapsulates the details of a block.
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
public class ResultDTO {

    private BlockDTO block;
    private Object justifications;

}
