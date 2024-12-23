package com.xauv.model;

import java.util.Date;

/**
 * @Author ling yue
 * @Date 2021/6/7 下午8:11
 * @Pkg com.xauv.model
 * @Desc description
 */
public class ModelDO {



    /**
     * 主键，自动增长
     */
    private Long id;

    /**
     * 创建时间，自动设置为当前时间
     */
    private Date gmtCreate;

    /**
     * 修改时间，自动更新为当前时间
     */
    private Date gmtModified;

    /**
     * 流程ID
     */
    private Long flowId;

    /**
     * 节点名称
     */
    private String name;

    /**
     * 节点代码
     */
    private String code;

    /**
     * 节点类型
     */
    private String type;

    /**
     * 节点内容
     */
    private String content;

}
