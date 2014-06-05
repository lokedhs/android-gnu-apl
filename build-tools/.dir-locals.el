((c++-mode . ((eval . (progn
                        (em-append-include-dirs (list (expand-file-name "~/src/apl-android/src")
                                                      (expand-file-name "/usr/local/jdk1.7.0_45/include")
                                                      (expand-file-name "/usr/local/jdk1.7.0_45/include/linux")))
                        (when (eq system-type 'darwin)
                          (em-append-include-dirs (list "/usr/local/Cellar/gettext/0.18.3.2/include")))
                        (flycheck-mode 1)
                        (company-mode 1))))))
