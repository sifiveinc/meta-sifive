DESCRIPTION = "SiFive HiFive Premier P550 Board Linux Kernel"
SECTION = "kernel"
LICENSE = "GPL-2.0-only"

FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

KBRANCH ?= "rel/kernel/hifive-premier-p550"
KBRANCH:hifive-premier-p550 = "rel/kernel/hifive-premier-p550"

SRCREV_machine ?= "4a22c048d3e410b5443d19c0db5bf34041e2d63c"
SRCREV_machine:hifive-premier-p550 = "4a22c048d3e410b5443d19c0db5bf34041e2d63c"
SRCREV_meta ?= "078f986aa4c328285abd0181cc21724d832a3ae0"

KCONFIG_MODE = "--alldefconfig"

KBUILD_DEFCONFIG ?= "hifive-premier-p550_defconfig"
KBUILD_DEFCONFIG:hifive-premier-p550 ?= "hifive-premier-p550_defconfig"

KERNEL_EXTRA_FEATURES ?= ""
KERNEL_FEATURES:remove = "cfg/fs/vfat.scc"
KERNEL_FEATURES:remove = "features/debug/printk.scc"
KERNEL_FEATURES:remove = "features/kernel-sample/kernel-sample.scc"

require recipes-kernel/linux/linux-yocto.inc

SRC_URI = "git://git@github.com/sifive/riscv-linux.git;protocol=ssh;name=machine;branch=${KBRANCH} \
           git://git.yoctoproject.org/yocto-kernel-cache;type=kmeta;name=meta;branch=yocto-6.6;destsuffix=${KMETA}"

LIC_FILES_CHKSUM = "file://COPYING;md5=6bc538ed5bd9a7fc9398086aedcd7e46"
LINUX_VERSION ?= "6.6.77"
LINUX_VERSION_EXTENSION = ""

PV = "${LINUX_VERSION}+git${SRCPV}"

KMETA = "kernel-meta"
KCONF_BSP_AUDIT_LEVEL = "2"
KCONF_AUDIT_LEVEL = "2"

INSANE_SKIP:kernel-vmlinux = "textrel"

COMPATIBLE_MACHINE = "(hifive-premier-p550)"
