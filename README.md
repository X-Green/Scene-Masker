# Fabric Example Mod

## Setup

For setup instructions please see the [fabric wiki page](https://fabricmc.net/wiki/tutorial:setup) that relates to the IDE that you are using.

## License

This template is available under the CC0 license. Feel free to learn from it and incorporate it in your own projects.

(''')

/masker
	-add/remove/query
		-single/range
			-[blockpos]/[blockpos] [blockpos]

	-clear

	-reverse
		-[blockpos] [blockpos]

	-cover/hide

	-setting
		-doMaskTrace/HideAllEntities/
			-True/False

	-whitelist
		-add/remove/check
			-minecraft:block/minecraft:entity	//所有方块、实体

	-changetool	//切换选中工具
		-所有物品

/*
-clear 清空mask
-reverse [pos1] [pos2] 反转以pos1 pos2为矩形的mask
-cover 开始透视
-hide 结束透视
-setting doMaskTrace 基于方块坐标放置mask(固定坐标)/基于方块本身放置mask(追踪方块,实时mask)
		 HideAllEntities 开始透视时是否隐藏所有实体
-whitelist 保护特定一类方块/实体不被透视
           check 查看白名单
*/

(''')
