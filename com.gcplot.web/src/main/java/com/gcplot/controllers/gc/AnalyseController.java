package com.gcplot.controllers.gc;

import com.gcplot.controllers.Controller;
import com.gcplot.repository.GCAnalyseRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

/**
 * @author <a href="mailto:art.dm.ser@gmail.com">Artem Dmitriev</a>
 *         8/13/16
 */
public class AnalyseController extends Controller {

    @PostConstruct
    public void init() {

    }

    @Autowired
    protected GCAnalyseRepository analyseRepository;
}
