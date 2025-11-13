require recipes-bsp/u-boot/u-boot-common.inc
require recipes-bsp/u-boot/u-boot.inc

DEPENDS += "bc-native dtc-native"

PROVIDES += "u-boot"

SRC_URI = "git://github.com/eswincomputing/u-boot.git;protocol=https;branch=u-boot-2024.01-EIC7X \
           file://0001-riscv-hifive_premier_p550-Update-boot-media-sequence.patch \
           file://0002-riscv-dts-eic770x-Fix-issues-running-upstream-OpenSB.patch \
           file://0003-riscv-p550-Use-ns16550-serial-driver-instead-of-eswi.patch \
           file://0004-riscv-cpu-eic7700-cache-Correct-cache-flush-size.patch \
           "

SRCREV = "EIC7X-2025.01"

PATCHTOOL = "git"

do_deploy:append () {
	install -m 755 ${B}/u-boot.dtb ${DEPLOYDIR}
}

COMPATIBLE_MACHINE = "(hifive-premier-p550)"
