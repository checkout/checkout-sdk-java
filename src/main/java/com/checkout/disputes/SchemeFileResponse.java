package com.checkout.disputes;

import com.checkout.common.Resource;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class SchemeFileResponse extends Resource {

    private String id;

    private List<SchemeFile> files;

}
