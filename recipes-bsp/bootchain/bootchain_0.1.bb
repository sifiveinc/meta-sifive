SUMMARY = "Bootchain image for HiFive Premier P550"
DESCRIPTION = "Combine the ESWIN DDR firmware, second-stage boot firmware and \
the OpenSBI fw_payload (with U-Boot) into bootloader_ddr5_secboot.bin using \
nsign, for flashing to the on-board SPI flash."

LICENSE = "CLOSED"

DEPENDS:append:hifive-premier-p550 = " opensbi-sifive nsign-native"
do_compile[depends] += " opensbi-sifive:do_deploy"

BRANCH = "master"
SRC_URI = "git://git@github.com/sifive/hifive-premier-p550-tools.git;protocol=ssh;branch=${BRANCH} \
           file://nsign.cfg \
           "

SRCREV = "5c81fa1ccc38b63d4ef71292d4ff5f971f976f4d"

B = "${WORKDIR}/build"

inherit deploy nopackages

PACKAGE_ARCH = "${MACHINE_ARCH}"

INHIBIT_DEFAULT_DEPS = "1"
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"

BOOTCHAIN_IMAGE = "bootloader_ddr5_secboot.bin"

do_configure[noexec] = "1"
do_install[noexec] = "1"

do_compile[cleandirs] = "${B}"
do_compile() {
	install -m 755 ${S}/ddr-fw/ddr_fw.bin ${B}/
	install -m 755 ${S}/second_boot_fw/second_boot_fw.bin ${B}/
	install -m 755 ${DEPLOY_DIR_IMAGE}/fw_payload.bin ${B}/
	install -m 644 ${UNPACKDIR}/nsign.cfg ${B}/
	nsign nsign.cfg
}

do_deploy() {
	install -m 755 ${B}/${BOOTCHAIN_IMAGE} ${DEPLOYDIR}/
}


addtask deploy after do_install

COMPATIBLE_MACHINE = "(hifive-premier-p550)"
