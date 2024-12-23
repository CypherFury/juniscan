/**
 * Data Transfer Object representing the structure of a blockchain RPC response.
 * Contains the protocol version, request ID, and the result of the response.
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
public class RpcResponseDTO {

    private String jsonrpc;
    private int id;
    private ResultDTO result;

}

