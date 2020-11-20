package com.yato.controller;

import com.yato.dto.Exposer;
import com.yato.dto.SeckillExcution;
import com.yato.dto.SeckillResult;
import com.yato.entity.Seckill;
import com.yato.enums.SeckillStateEnum;
import com.yato.exception.RepeatKillException;
import com.yato.exception.SeckillClosedException;
import com.yato.service.ISeckillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 *   GET /seckill/list    秒杀列表
 *   GET /seckill/{id}/detail>   详情页
 *   GET /seckill/time/now>   系统时间
 *   POST /seckill/{id}exposer>  暴露秒杀
 *   POST /seckill/{id}/{md5}/execution>     执行秒杀
 */
@Controller
@RequestMapping("/seckill")
public class SeckillController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private ISeckillService seckillService;

    @GetMapping("/list")
    public String list(Model model){
        List<Seckill> list = seckillService.getSeckillList();
        model.addAttribute("list", list);
        return "list";
    }
    @GetMapping("/{seckillId}/detail")
    public String detail(@PathVariable("seckillId") Long seckillId, Model model){
        if (null == seckillId){
            // 如果没有传入 id，重定向回列表页
            return "redirect:/seckill/list";
            // return "forward:/seckill/list";
        }
        Seckill seckill = seckillService.getById(seckillId);
        if (null == seckill){
            // 商品不存在，重定向回列表页
            return "redirect:/seckill/list";
        }
        model.addAttribute("seckill", seckill);
        return "detail";
    }

    @GetMapping("/time/now")
    @ResponseBody
    public SeckillResult<Long> time(Model model){
        Date now = new Date();
        return new SeckillResult<Long>(true, now.getTime());
    }

    /**
     * @param seckillId
     * @return 返回 json类型数据
     */
    @PostMapping(value = "/{seckillId}/exposer", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public SeckillResult<Exposer> exposer(@PathVariable("seckillId") Long seckillId) {
        SeckillResult<Exposer> result = null;
        try {
            Exposer exposer = seckillService.exportSeckillUrl(seckillId);
            result = new SeckillResult<Exposer>(true, exposer);
        }catch (Exception e){
            logger.error(e.getMessage(), e);
            result = new SeckillResult<>(false, e.getMessage());
        };
        return result;
    }
    @PostMapping(value = "/{seckillId}/{md5}/excution", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public SeckillResult<SeckillExcution> excution(
                @PathVariable("seckillId") Long seckillId,
                @PathVariable("md5") String md5,
                @CookieValue(value = "userPhone", required = false) Long phone){
        // 可以使用 springmvc valid
        if (null == phone){
            return new SeckillResult<SeckillExcution>(false, "未注册");
        }
        SeckillResult<SeckillExcution> result = null;
        try {
            SeckillExcution excution = seckillService.excuteSeckill(seckillId, phone, md5);
            return new SeckillResult<>(true, excution);
        } catch (RepeatKillException e){
            SeckillExcution excution = new SeckillExcution(seckillId, SeckillStateEnum.REPEAT_KILL);
            return new SeckillResult<>(true, excution);
        } catch (SeckillClosedException e){
            SeckillExcution excution = new SeckillExcution(seckillId, SeckillStateEnum.END);
            return new SeckillResult<>(true, excution);
        } catch (Exception e){
            logger.error(e.getMessage(), e);
            return new SeckillResult<SeckillExcution>(true,
                    new SeckillExcution(seckillId, SeckillStateEnum.INNER_ERROR));
        }
    }

}
