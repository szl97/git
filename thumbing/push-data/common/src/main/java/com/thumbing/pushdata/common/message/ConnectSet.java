package com.thumbing.pushdata.common.message;

import com.thumbing.pushdata.common.constants.OperationEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


/**
 * @author Stan Sai
 * @date 2020-06-21
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConnectSet extends NodeMessage<ConnectSet> {

    private List<Long> userIds;

    private String name;

    private OperationEnum operation;

    @Override
    protected Type type() {
        return Type.CS;
    }

    @Override
    protected ConnectSet getThis() {
        return this;
    }
}
