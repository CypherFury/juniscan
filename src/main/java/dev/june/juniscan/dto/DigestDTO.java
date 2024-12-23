/**
 * Data Transfer Object for the digest section of a blockchain block.
 * Contains the logs, which store metadata or system information related to the block.
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
public class DigestDTO {

    private List<String> logs;

}
