<p align="center">
  <a href="https://github.com/dong-jianbin/flow-pro/issues" target="_blank"><img src="https://img.shields.io/github/issues/dong-jianbin/flow-pro" alt="GitHub issues" class="no-zoom"></a>
  <a href="https://github.com/dong-jianbin/flow-pro/stargazers" target="_blank"><img src='https://img.shields.io/github/stars/dong-jianbin/flow-pro' alt='GitHub stars' class="no-zoom"></a>
  <a href="ttps://github.com/dong-jianbin/flow-pro/network" target="_blank"><img src='https://img.shields.io/github/forks/dong-jianbin/flow-pro' alt='GitHub forks' class="no-zoom"></a>
  <a href="https://github.com/dong-jianbin/flow-pro/blob/main/LICENSE" target="_blank"><img src="https://img.shields.io/github/license/dong-jianbin/flow-pro" alt="GitHub license" class="no-zoom"></a>
</p>

## 目标
*零代码 + 全配置 = ❤️工作流(复杂业务+简单操作)*

## 主要功能
> 详细内容见[项目文档https://flowpro.ddoogg.cn/](https://flowpro.ddoogg.cn/)
### 复杂业务流程

1. 企业级复杂度的业务表单生成器  
   JeecgBoot online代码生成器非常强大：支持单表、一对一、一对多、树模型生成,生成的代码包括前台和后台,生成后直接使用,无需修改,具体配置请参见 JeecgBoot 开发文档： http://doc.jeecg.com。本项目对JeecgBoot online代码生成器的模板进行了改造，主要修改了前端项目vue部分，以便生成的代码无缝的对接到工作流中。业务表单中额外需要两个字段：bpm_status 通过这个标识在模板中区分工作流表单和普通表单。bpm_id 工作流标识，用于标识一个工作流实例包含多条业务数据。  
![表单生成器](https://cdn.jsdelivr.net/gh/dong-jianbin/drawing-bed/img/20211113055813.png)
2. 业务表单和工作流桥接  
   用于对接工作流和业务表单。根据表单生成器生成代码的路径来配置表单的路由，路径，数据库主表名称和子表名称。
   配置路径需参照代码生成的物理位置,以及vue代码拷贝到前端项目的位置。  
   ![桥接](https://cdn.jsdelivr.net/gh/dong-jianbin/drawing-bed/img/20211113055648.png)
3. 高可用工作流  
   实现全配置。流程判断灵活，主表记录的所有字段值都可作为条件判断依据。
   工作流和业务表单是低耦合的。工作流对表单样式理论上是没有限制的，可以是单页，多tab子页，树状页，等等。
   工作流对表单的数据的复杂度不做限制，原则一张主表n张子表的结构，表中的记录条数不做限制
   通过灵活的权限控制，对数据的横向和纵向切割，适用不同工作流节点做不同的业务。
   指派人界面可配置，抽取出常用的指派场景  
   ![工作流](https://cdn.jsdelivr.net/gh/dong-jianbin/drawing-bed/img/20211113055931.png)

### 简单业务流程

简单业务流程，侧重于简单操作，适用于直接用户，常用的简单业务流转。高度集成的操作界面包括基础设置、表单设计、流程设计、高级设计。流程的所有节点显示一个业务表单，业务表单对应一张数据库业务表的一条记录  
1. 基础设置
![基础设置](https://cdn.jsdelivr.net/gh/dong-jianbin/drawing-bed/img/20211112135049.png)
2. 表单设计
![表单设计](https://cdn.jsdelivr.net/gh/dong-jianbin/drawing-bed/img/20211112141050.png)
3. 流程设计
![流程设计](https://cdn.jsdelivr.net/gh/dong-jianbin/drawing-bed/img/20211112141114.png)
4. 高级设计
![高级设计](https://cdn.jsdelivr.net/gh/dong-jianbin/drawing-bed/img/20211112141212.png)