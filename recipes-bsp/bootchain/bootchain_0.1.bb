LICENSE = "CLOSED"

DEPENDS:append:hifive-premier-p550 = " opensbi-sifive-hf-prem nsign-native"
do_compile[depends] += " opensbi-sifive-hf-prem:do_deploy"

BRANCH = "master"
SRC_URI = "git://git@github.com/sifive/hifive-premier-p550-tools.git;branch=${BRANCH};protocol=ssh"
SRCREV = "5c81fa1ccc38b63d4ef71292d4ff5f971f976f4d"

SRC_URI:append = " file://nsign.cfg"

inherit deploy
inherit allarch

INHIBIT_DEFAULT_DEPS = "1"
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"

do_configure[noexec] = "1"
do_install[noexec] = "1"

do_compile() {
	install -m 755 ${S}/ddr-fw/ddr_fw.bin ${UNPACKDIR}/
	install -m 755 ${S}/second_boot_fw/second_boot_fw.bin ${UNPACKDIR}/
	install -m 755 ${DEPLOY_DIR_IMAGE}/fw_payload.bin ${UNPACKDIR}/
	cd ${UNPACKDIR} && nsign nsign.cfg
}

do_deploy() {
	install -m 755 ${UNPACKDIR}/bootloader_ddr5_secboot.bin ${DEPLOYDIR}/
}

INSANE_SKIP = "arch"

addtask deploy after do_install

COMPATIBLE_MACHINE = "(hifive-premier-p550)"
